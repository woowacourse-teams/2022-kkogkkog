const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const { merge } = require('webpack-merge');

const common = require('./webpack.common');

require('dotenv').config();

module.exports = merge(common, {
  mode: 'development',
  devtool: 'eval',
  plugins: [new ReactRefreshWebpackPlugin()],
});
