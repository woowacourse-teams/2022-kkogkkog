const { merge } = require('webpack-merge');

const common = require('./webpack.common');

const CompressionPlugin = require('compression-webpack-plugin');

require('dotenv').config();

module.exports = merge(common, {
  mode: 'production',
  plugins: [new CompressionPlugin()],
});
