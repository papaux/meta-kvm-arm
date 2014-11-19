require recipes-kernel/linux/linux-hardkernel.inc

COMPATIBLE_MACHINE = "odroidxu3"

# Specify dtb file
KERNEL_DEVICETREE_odroidxu3-kvm = "exynos5422-odroidxu3.dtb"

BRANCH = "odroidxu3-3.10.y"

# Commit from Nov 6 2014
SRCREV = "b7a621164b20f493bf5cc2de7e5aa81dcee13967"
PV = "3.10.59"
