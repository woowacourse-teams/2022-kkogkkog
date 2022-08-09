const path = require('path');
const Dotenv = require('dotenv-webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');
const webpack = require('webpack');

console.log('process.env.NODE_ENV', process.env.NODE_ENV);

module.exports = (env, args) => {
  const isDevelopment = args.mode === 'development';

  return {
    entry: './src/index.tsx',
    output: {
      path: path.resolve(__dirname, 'build'),
      publicPath: '/',
      filename: 'bundle.[hash].js',
      clean: true,
    },
    devServer: {
      static: {
        directory: path.join(__dirname, 'build'),
      },
      compress: true,
      port: 3000,
      historyApiFallback: true,
      host: 'localhost',
    },
    resolve: {
      extensions: ['.js', '.jsx', '.ts', '.tsx'],
      modules: ['node_modules'],
      alias: {
        '@': path.resolve(__dirname, 'src/'),
      },
    },
    performance: {
      hints: false,
      maxEntrypointSize: 512000,
      maxAssetSize: 512000,
    },
    module: {
      rules: [
        {
          test: /\.(js|jsx|ts|tsx)$/,
          use: {
            loader: require.resolve('babel-loader'),
            options: {
              envName: isDevelopment ? 'development' : 'production',
              presets: [
                '@babel/preset-env',
                ['@babel/preset-react', { runtime: 'automatic', importSource: '@emotion/react' }],
                '@babel/preset-typescript',
              ],
              plugins: [
                '@emotion/babel-plugin',
                isDevelopment && require.resolve('react-refresh/babel'),
              ].filter(Boolean),
            },
          },
        },
      ],
    },
    plugins: [
      new HtmlWebpackPlugin({
        template: './public/index.html',
        filename: 'index.html',
      }),
      isDevelopment && new ReactRefreshWebpackPlugin(),
      new CopyPlugin({
        patterns: [
          {
            from: 'public/',
            to: '',
            globOptions: {
              ignore: ['**/*.html'],
            },
          },
        ],
      }),
      new Dotenv(),
      new webpack.DefinePlugin({
        PRODUCT_ENV: JSON.stringify(process.env.NODE_ENV),
      }),
    ].filter(Boolean),
  };
};
