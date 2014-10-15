require recipes-kernel/linux/linux-hardkernel.inc

COMPATIBLE_MACHINE = "odroidxu3"

# Specify dtb file
KERNEL_DEVICETREE_odroidxu3-kvm = "exynos5422-odroidxu3.dtb"

BRANCH = "odroidxu3-3.10.y"

# Commit from Wed Oct 08 2014
SRCREV = "c4ed13b1474a09066057ef01f301d79bd3fe4161"
PV = "3.10.54"
