import styled from '@emotion/styled';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogCreateForm from '@/@components/kkogkkog/KkogKkogCreateForm';
import { useKkogKKogForm } from '@/@hooks/kkogkkog/useKkogKkogForm';
import theme from '@/styles/theme';
import { KkogKkogType } from '@/types/domain';
import { THUMBNAIL } from '@/utils/constants/kkogkkog';

export const modifiers = ['재미있게', '활기차게', '한턱쏘는'] as const;

export const colors = [
  theme.colors.white_100,
  theme.colors.primary_100,
  theme.colors.primary_200,
  theme.colors.primary_300,
  theme.colors.primary_400,
  theme.colors.primary_500,
] as const;

export const couponTypes = Object.entries(THUMBNAIL).map(([key, value]) => ({
  type: key as KkogKkogType,
  imageURL: value,
}));

const KkogkkogCreatePage = () => {
  const {
    state: { receiverList, couponType, modifier, color, message },
    changeHandler: {
      onSelectType,
      onSelectModifier,
      onSelectColor,
      onChangeMessage,
      onSelectReceiver,
    },
    submitHandler: { onSubmitCreateForm },
  } = useKkogKKogForm();

  return (
    <PageTemplate title='꼭꼭 만들기'>
      <Styled.Root>
        {/* <Styled.Inner>
          <KkogKkogItem
            id={1}
            senderName={me?.name}
            message={message}
            backgroundColor={color}
            modifier={modifier}
            thumbnail={THUMBNAIL[couponType]}
            couponType={couponType}
          />
        </Styled.Inner> */}
        <KkogKkogCreateForm
          currentReceiverList={receiverList}
          currentType={couponType}
          currentModifier={modifier}
          currentColor={color}
          currentMessage={message}
          onSelectReceiver={onSelectReceiver}
          onSelectType={onSelectType}
          onSelectModifier={onSelectModifier}
          onSelectColor={onSelectColor}
          onChangeMessage={onChangeMessage}
          onSubmitCreateForm={onSubmitCreateForm}
        />
      </Styled.Root>
    </PageTemplate>
  );
};

export default KkogkkogCreatePage;

export const Styled = {
  Root: styled.div`
    padding: 0 20px 40px 20px;

    border-radius: 4px;
  `,
  Inner: styled.div`
    padding: 25px 0;
  `,
};
