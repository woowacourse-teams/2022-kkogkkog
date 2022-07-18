import styled from '@emotion/styled';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogCreateForm from '@/@components/kkogkkog/KkogKkogCreateForm';
import { useKkogKkogForm } from '@/@hooks/kkogkkog/useKkogKkogForm';

const KkogkkogCreatePage = () => {
  const {
    state: { receiverList, couponType, modifier, color, message },
    changeHandler: {
      onSelectReceiver,
      onSelectType,
      onSelectModifier,
      onSelectColor,
      onChangeMessage,
    },
    submitHandler: { create: onSubmitForm },
  } = useKkogKkogForm();

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
          onSubmitCreateForm={onSubmitForm}
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
