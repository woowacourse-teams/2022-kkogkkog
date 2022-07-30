import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useEffect, useRef } from 'react';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogCreateForm from '@/@components/kkogkkog/KkogKkogCreateForm';
import BigKkogKkogItem from '@/@components/kkogkkog/KkogKkogItem/big';
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

  const elementRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    elementRef.current?.scrollTo({ left: elementRef.current?.scrollWidth, behavior: 'smooth' });
  }, [receiverList]);

  return (
    <PageTemplate title='꼭꼭 보내기'>
      <Styled.Root>
        <Styled.PreviewContainer ref={elementRef}>
          {receiverList.length === 0 ? (
            <Styled.GuideContainer>꼭꼭을 완성해보세요!</Styled.GuideContainer>
          ) : (
            receiverList.map(receiver => (
              <BigKkogKkogItem.Preview
                key={receiver.id}
                receiver={receiver}
                backgroundColor={color}
                message={message}
                modifier={modifier}
                couponType={couponType}
              />
            ))
          )}
        </Styled.PreviewContainer>
        <Styled.Inner>
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
        </Styled.Inner>
      </Styled.Root>
    </PageTemplate>
  );
};

export default KkogkkogCreatePage;

export const Styled = {
  Root: styled.div`
    border-radius: 4px;
  `,
  Inner: styled.div`
    padding: 0 20px 40px 20px;
  `,
  PreviewContainer: styled.div`
    display: flex;
    padding: 25px;

    height: 180px;

    overflow-x: scroll;

    & > div:not(:last-child) {
      margin-right: 20px;
    }
  `,
  GuideContainer: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;

    width: 100%;

    ${({ theme }) => css`
      color: ${theme.colors.light_grey_200};

      box-shadow: ${theme.shadow.type_2};

      border-radius: 20px;
    `}
  `,
};
