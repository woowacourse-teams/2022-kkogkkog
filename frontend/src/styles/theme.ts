const colors = {
  primary_500: '#FF5622',
  primary_400: '#FF7020',
  primary_400_opacity: '#FF702016',
  primary_300: '#FF9620',
  primary_300_opacity: '#FF962047',
  primary_200: '#FFB25B',
  primary_100: '#FFC17B',

  light_grey_200: '#C1C1C1',
  light_grey_100: '#DFDFDF',

  white_100: '#FFFFFF',

  grey_400: '#555555',
  grey_300: '#6F6F6F',
  grey_200: '#8B8B8B',
  grey_100: '#A5A5A5',

  drak_grey_200: '#242424',
  drak_grey_100: '#3D3D3D',

  green_500: '#51D230',

  red_800: '#E81300',

  background_4: '#F5F4F3',
  background_3: '#F7F7F7',
  background_2: '#F6F4EE',
  background_1: '#FAFAF6',
  background_0: '#FDFDFD',

  green_500: '#51D230',
} as const;

const layers = {
  header: 4900,
  dimmed: 5000,
} as const;

const shadow = {
  type_1: '0px 0px 2px rgba(0, 0, 0, 0.2)',
  type_2: '0px 8px 20px rgba(0, 0, 0, 0.1)',
  type_3: '0px 0px 4px rgba(0, 0, 0, 0.12)',
  type_4: '0px 0px 2px rgba(0, 0, 0, 0.24)',
  type_5: '0px 0px 2px rgba(0, 0, 0, 0.2)',
  type_6: '0px 4px 12px rgba(0, 0, 0, 0.16)',
};

const theme = {
  colors,
  layers,
  shadow,
};

export default theme;
