DESCRIPTION = "Image containing tools required for KVM virtualization with libvirt support. Comes with other benchmarking / performance debugging tools."

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    ${ROOTFS_PKGMANAGE_BOOTSTRAP} \
    qemu \
    libvirt \
    libvirt-libvirtd \
    libvirt-virsh \
    perf \
    iperf \
    procps \
    coreutils \
    "
#    kernel-module-kvm \
#    kernel-module-kvm-intel \
#    kernel-module-kvm-amd \
#    "

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE = "8192"

ROOTFS_POSTPROCESS_COMMAND += "remove_packaging_data_files ; "
