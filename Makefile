GITHUB_USER=Avramenko-Vitaliy
REPO=rd-capstone
NAMESPACE=default

bootstrap:
	flux bootstrap github \
      --owner=$(GITHUB_USER) \
      --repository=$(REPO) \
      --branch=dev \
      --path=./infra/clusters/rd-cluster \
      --personal

build-app:
	mvn clean package -Pdocker

lint:
	helm lint ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)

template:
	helm template ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)

build-infra:
	flux build ks rd-dev --path ./infra/clusters/rd-cluster --kustomization-file ./infra/clusters/rd-cluster/app-dev.yaml --dry-run
