const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');
const { DefinePlugin } = require('webpack');

require('dotenv').config();

module.exports = {
  entry: './src/index.tsx',
  output: {
    path: path.join(__dirname, '..', 'build'),
    publicPath: '/',
    filename: 'bundle.[hash].js',
    clean: true,
  },
  devServer: {
    static: {
      directory: path.join(__dirname, '..', 'build'),
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
      '@': path.join(__dirname, '..', 'src/'),
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
      template: path.join(__dirname, '..', 'public/index.html'),
      filename: 'index.html',
    }),
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
    new DefinePlugin({
      'process.env': JSON.stringify(process.env),
      PRODUCT_ENV: JSON.stringify(process.env.NODE_ENV),
    }),
  ],
};
