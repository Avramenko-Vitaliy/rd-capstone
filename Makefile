GITHUB_USER=Avramenko-Vitaliy
REPO=rd-capstone
NAMESPACE=default

bootstrap:
	flux bootstrap github \
      --owner=$(GITHUB_USER) \
      --repository=$(REPO) \
      --branch=main \
      --path=./infra/clusters/rd-cluster \
      --personal

build:
	mvn clean package -Pdocker

lint:
	helm lint ./infra/charts/rd-capstone -f ./infra/charts/rd-capstone/values.yaml -n $(NAMESPACE)

template:
	helm template ./infra/charts/rd-capstone -f ./infra/charts/rd-capstone/values.yaml -n $(NAMESPACE)
