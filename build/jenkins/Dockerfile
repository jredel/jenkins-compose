FROM jenkins/jenkins:lts
USER root
RUN <<EOF 
apt-get update && apt-get install -y lsb-release
curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc https://download.docker.com/linux/debian/gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.asc] https://download.docker.com/linux/debian $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
apt-get update
apt-get install -y docker-ce-cli
rm -rf /var/lib/apt/lists/*
EOF
USER jenkins
# RUN jenkins-plugin-cli --plugins "docker-workflow:521.v1a_a_dd2073b_2e"
RUN jenkins-plugin-cli --plugins "docker-workflow:572.v950f58993843"