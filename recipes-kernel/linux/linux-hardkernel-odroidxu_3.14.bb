require recipes-kernel/linux/linux-hardkernel.inc

COMPATIBLE_MACHINE = "odroidxu"

# Specify dtb file
KERNEL_DEVICETREE_odroidxu-kvm = "exynos5410-odroidxu.dtb"

BRANCH = "odroid-3.14.y-linaro"
SRCREV = "f2083913678cde7c497401c148c0dd53106d9746"
PV = "3.14.0"
