GITHUB_USER=Avramenko-Vitaliy
REPO=rd-capstone
NAMESPACE=default
MAIN_BRANCH=dev
BRANCH=prod

bootstrap:
	flux bootstrap github \
	  --read-write-key=true \
	  --components-extra=image-reflector-controller,image-automation-controller \
      --owner=$(GITHUB_USER) \
      --repository=$(REPO) \
      --branch=$(MAIN_BRANCH) \
      --path=./infra/clusters/rd-cluster \
      --personal

add-branch:
	flux create source git prod \
      --url=ssh://git@github.com/Avramenko-Vitaliy/rd-capstone.git \
      --branch=$(BRANCH) \
      --secret-ref=flux-system \
      --namespace=flux-system

build-app:
	mvn clean package -Pdocker

lint:
	helm lint ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)

template:
	helm template ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)

build-ks:
	flux build ks rd-dev --path ./infra/clusters/rd-cluster --kustomization-file ./infra/clusters/rd-cluster/app-dev.yaml --dry-run

rc-dev:
	flux reconcile ks rd-dev --with-source

rc-prod:
	flux reconcile ks rd-prod --with-source

rc-fs:
	flux reconcile ks flux-system --with-source

rc-po:
	flux reconcile ks pg-operator --with-source

rc: rc-fs rc-po rc-dev rc-prod

init: bootstrap add-branch
