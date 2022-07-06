import KkogKkogItem from '.';

export default {
  component: KkogKkogItem,
  title: 'KkogKkogItem',
  decorators: [
    Story => (
      <div style={{ width: '414px', height: '100vh' }}>
        <Story />
      </div>
    ),
  ],
};

const Template = args => <KkogKkogItem {...args} />;

export const Default = Template.bind({});
Default.args = {
  id: 1,
  senderName: '준찌',
  receiverName: '시지프',
  backgroundColor: 'red',
  modifier: '한턱쏘는',
  type: '커피',
  thumbnail: '',
};
