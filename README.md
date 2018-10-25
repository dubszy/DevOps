# DevOps Tools

Suite of devops tools for personal and professional projects.

## Directory Structure

### config/*
Configuration files for setting up services.

### data/persistent/*
Persistent data storage for Docker containers, linked to containers as volumes
in `docker-compose.yml`.

### docker/*
Dockerfiles for starting Docker containers described in `docker-compose.yml`.

### jenkinsci-docker/*
[Jenkins Docker](https://github.com/jenkinsci/docker) submodule. The Dockerfiles
provided in this repo are not used because more control over the container is
required for the purposes of this project, but several of the scripts in this
repo are required for Jenkins to start and run in the Docker container.
