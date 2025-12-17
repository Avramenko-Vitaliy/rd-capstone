GITHUB_USER=Avramenko-Vitaliy
REPO=rd-capstone
NAMESPACE=default

bootstrap:
	flux bootstrap github \
	  --read-write-key=true \
	  --components-extra=image-reflector-controller,image-automation-controller \
      --owner=$(GITHUB_USER) \
      --repository=$(REPO) \
      --branch=dev \
      --path=./infra/clusters/rd-cluster \
      --personal

build:
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

rc-cnpg:
	flux reconcile ks cnpg-operator --with-source

rc-fs:
	flux reconcile ks flux-system --with-source

rc-is:
	flux reconcile ks image-scanning --with-source

rc: rc-is rc-cnpg rc-dev rc-prod rc-fs
