GITHUB_USER=Avramenko-Vitaliy
REPO=rd-capstone
NAMESPACE=default
MAIN_BRANCH=dev
BRANCH=prod
KS_FILE=app-dev.yaml
KS_NAME=rd-dev

#---------------- Flux CD --------------
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

build-ks:
	flux build ks $(KS_NAME) --path ./infra/clusters/rd-cluster --kustomization-file ./infra/clusters/rd-cluster/$(KS_FILE) --dry-run

rc-dev:
	flux reconcile ks rd-dev

rc-prod:
	flux reconcile ks rd-prod

rc-fs:
	flux reconcile ks flux-system

rc-po:
	flux reconcile ks pg-operator

rc-cm:
	flux reconcile ks cert-manager

init: bootstrap add-branch

rc: rc-fs rc-cm rc-po rc-dev rc-prod

#---------------- Helm --------------
lint:
	helm lint ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)

template:
	helm template ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)

#---------------- Application --------------
build-app:
	mvn clean package -Pdocker

#---------------- Kubernetes --------------
port-forward:
	kubectl port-forward -n kube-system svc/traefik 8443:443
