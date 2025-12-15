GITHUB_USER=Avramenko-Vitaliy
REPO=rd-capstone
NAMESPACE=default

bootstrap:
	flux bootstrap github \
      --owner=$(GITHUB_USER) \
      --repository=$(REPO) \
      --branch=master \
      --path=./infra/clusters/rd-cluster \
      --personal

build:
	mvn clean package -Pdocker

lint:
	helm lint ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)

template:
	helm template ./infra/charts/rd-app -f ./infra/charts/rd-app/values.yaml -n $(NAMESPACE)
