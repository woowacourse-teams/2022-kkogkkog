const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');
const webpack = require('webpack');
const CompressionPlugin = require('compression-webpack-plugin');

require('dotenv').config();

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
      new webpack.DefinePlugin({
        'process.env': JSON.stringify(process.env),
        PRODUCT_ENV: JSON.stringify(process.env.NODE_ENV),
      }),
      new CompressionPlugin(),
    ].filter(Boolean),
  };
};
