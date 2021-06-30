# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  #config.vm.define "vm-client" do |vmc|
  #vmc.vm.box = "bento/ubuntu-20.04"
  #vmc.vm.hostname = "vm-client.project.com"
  #vmc.vm.network "private_network", ip: "192.168.56.100"

  #vmc.vm.provider "virtualbox" do |v|
  #  v.name = "vm-client"
  #  v.customize ["modifyvm", :id, "--groups", "/Klient-Serwer"]
  #  v.customize ["modifyvm", :id, "--cpus", "2"]
  #  v.customize ["modifyvm", :id, "--memory", "4096"]
  #  end
  #
  #  vmc.vm.provision "shell", path: "client-init.sh"
  #end

  config.vm.define "db-server" do |vmc|
    vmc.vm.box = "ubuntu/xenial64"
    vmc.vm.hostname = "vm-server.project.com"
    vmc.vm.network "public_network", :bridge => "eth0", ip: "192.168.56.100"
    vmc.vm.network "forwarded_port", guest: 3306, host: 3306
    vmc.vm.network "forwarded_port", guest: 80, host: 8306
  
    vmc.vm.provider "virtualbox" do |v|
      v.name = "vm-server"
      v.customize ["modifyvm", :id, "--groups", "/Klient-Serwer"]
      v.customize ["modifyvm", :id, "--cpus", "2"]
      v.customize ["modifyvm", :id, "--memory", "4096"]
      end
  
      vmc.vm.provision "shell", path: "server-init.sh"
    end
end
