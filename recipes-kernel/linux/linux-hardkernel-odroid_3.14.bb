SECTION = "kernel"
DESCRIPTION = "Linux kernel for ODROID devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc

# Pull in the devicetree files into the rootfs
RDEPENDS_kernel-base += "kernel-devicetree"



# Specify dtb file
KERNEL_DEVICETREE_odroidxu-kvm = "exynos5410-odroidxu.dtb"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

COMPATIBLE_MACHINE = "odroidxu"

S = "${WORKDIR}/git"

BRANCH = "odroid-3.14.y-linaro"

SRCREV = "f2083913678cde7c497401c148c0dd53106d9746"
PV = "3.14.0"

# Append to the MACHINE_KERNEL_PR so that a new SRCREV will cause a rebuild
MACHINE_KERNEL_PR_append = "a+gitr${SRCPV}"
PR = "${MACHINE_KERNEL_PR}"

SRC_URI = "git://github.com/hardkernel/linux.git;protocol=https;branch=${BRANCH} \
           file://defconfig \
          "
