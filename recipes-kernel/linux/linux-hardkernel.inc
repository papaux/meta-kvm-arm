SECTION = "kernel"
DESCRIPTION = "Linux kernel for ODROID devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc

# Pull in the devicetree files into the rootfs
RDEPENDS_kernel-base += "kernel-devicetree"


KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/git"

# Append to the MACHINE_KERNEL_PR so that a new SRCREV will cause a rebuild
MACHINE_KERNEL_PR_append = "a+gitr${SRCPV}"
PR = "${MACHINE_KERNEL_PR}"

SRC_URI = "git://github.com/hardkernel/linux.git;protocol=https;branch=${BRANCH} \
           file://defconfig \
          "
