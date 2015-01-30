DESCRIPTION = "Image containing tools required for KVM labs at HEIA-FR. Comes with other benchmarking / performance debugging tools as well as build tools."

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    ${ROOTFS_PKGMANAGE_BOOTSTRAP} \
    qemu \
    libvirt \
    libvirt-libvirtd \
    libvirt-virsh \
    perf \
    iperf \
    iozone3 \
    procps \
    coreutils \
    git \
    valgrind \
    packagegroup-core-buildessential \
    "
#    kernel-module-kvm \
#    kernel-module-kvm-intel \
#    kernel-module-kvm-amd \
#    "

# include kernel and u-boot, is this the correct way to do it ? 
DEPENDS += "virtual/kernel u-boot"

IMAGE_FEATURES += "ssh-server-openssh"

LICENSE = "MIT"

inherit core-image 

IMAGE_ROOTFS_SIZE = "512000"
