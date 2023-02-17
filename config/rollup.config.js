import { nodeResolve } from '@rollup/plugin-node-resolve';

export default [
    {
        input: 'config/hotwired.config.js',
        output: {
            file: 'src/main/resources/static/dist/turbo.js',
            format: 'umd',
            name: 'hotwired.turbo'
        },
        plugins: [
            nodeResolve()
        ],
    }
]