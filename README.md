### PROD ENV
[![Build API](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-api.yml/badge.svg?branch=dev)](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-api.yml)
[![Build UI](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-ui.yml/badge.svg?branch=dev)](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-ui.yml)

### DEV ENV
[![Build API](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-api.yml/badge.svg?branch=prod)](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-api.yml)
[![Build UI](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-ui.yml/badge.svg?branch=prod)](https://github.com/Avramenko-Vitaliy/rd-capstone/actions/workflows/build-ui.yml)

# Project Description

This project implements a GitOps-based deployment infrastructure using FluxCD for continuous delivery.

## Application

The own application consists of two main parts:

- **UI** - User Interface component
- **API** - Backend API service

## Database

PostgreSQL database managed by a PostgreSQL operator (pg-operator).

## Automation

### GitHub Actions

GitHub Actions CI/CD pipeline builds and pushes Docker images for the application components (UI and API) to the
container registry on every commit to the repository.

### FluxCD ImageUpdateAutomation

FluxCD ImageUpdateAutomation automatically updates container images across environments:

- **Dev environment**: Monitors and updates from `dev` branch
- **Prod environment**: Monitors and updates from `prod` branch

The automation runs every 10 minutes and commits image updates back to the respective Git branches.

## Infrastructure

- Deployments managed via Kustomize overlays (dev/prod)
- Certificate management via cert-manager
- Target namespaces: `dev`, `prod` and `flux-system`

## Proofs of working
![result.png](assets/result.png)
