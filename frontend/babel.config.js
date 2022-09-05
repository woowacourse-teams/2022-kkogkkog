module.exports = api => {
  return {
    presets: [
      '@babel/preset-env',
      ['@babel/preset-react', { runtime: 'automatic', importSource: '@emotion/react' }],
      '@babel/preset-typescript',
    ],
    plugins: [
      '@emotion/babel-plugin',
      api.env('development') && require.resolve('react-refresh/babel'),
    ].filter(Boolean),
  };
};
