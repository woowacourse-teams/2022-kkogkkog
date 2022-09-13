export const rgb2hex = (rawRgba: string) => {
  const rgba = rawRgba.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+\.{0,1}\d*))?\)$/);

  if (!rgba) {
    throw new Error('Not rgba format');
  }

  return `#${rgba
    .slice(1)
    .map((n, i) =>
      (i === 3 ? Math.round(parseFloat(n) * 255) : parseFloat(n))
        .toString(16)
        .padStart(2, '0')
        .replace('NaN', '')
    )
    .join('')
    .toUpperCase()}`;
};
