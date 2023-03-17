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
    },
    {
        input: 'node_modules/marked/src/marked.js',
        output: {
            file: 'src/main/resources/static/dist/marked.js',
            format: 'umd',
            name: 'marked'
        },
        plugins: [
            nodeResolve()
        ]
    }
]