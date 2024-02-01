const Layout = () => import('@/layout/index.vue')
const category = () => import('@/views/product/category.vue')
const brand = () => import('@/views/product/brand.vue')

export default [
    {
        path: '/product',
        component: Layout,
        name: 'product',
        meta: {
            title: '商品管理',
        },
        icon: 'Histogram',
        children: [
            {
                path: '/category',
                name: 'category',
                component: category,
                meta: {
                    title: '分类管理',
                },
            },
            {
                path: '/brand',
                name: 'brand',
                component: brand,
                meta: {
                    title: '品牌管理',
                },
            }
        ],
    },
]