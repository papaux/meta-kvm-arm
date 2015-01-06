require recipes-kernel/linux/linux-hardkernel.inc

COMPATIBLE_MACHINE = "odroidxu"

# Specify dtb file
KERNEL_DEVICETREE_odroidxu-kvm = "exynos5410-odroidxu.dtb"

BRANCH = "odroid-3.13.y"
SRCREV = "1dc0fc76e17f8514e4be7da09b9823815c230900"
PV = "3.13.11"
