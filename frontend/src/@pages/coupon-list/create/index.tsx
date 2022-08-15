import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useEffect, useRef } from 'react';

import PageTemplate from '@/@components/@shared/PageTemplate';
import CouponCreateForm from '@/@components/coupon/CouponCreateForm';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import { useCouponForm } from '@/@hooks/coupon/useCouponForm';

const CouponCreatePage = () => {
  const {
    state: { receiverList, couponType, hashtag, color, message },
    changeHandler: {
      onSelectReceiver,
      onSelectType,
      onSelectHashtag,
      onSelectColor,
      onChangeMessage,
    },
    submitHandler: { create: onSubmitForm },
  } = useCouponForm();

  const elementRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    elementRef.current?.scrollTo({ left: elementRef.current?.scrollWidth, behavior: 'smooth' });
  }, [receiverList]);

  return (
    <PageTemplate title='쿠폰 보내기'>
      <Styled.Root>
        <Styled.PreviewContainer ref={elementRef}>
          {receiverList.length === 0 ? (
            <Styled.GuideContainer>쿠폰을 완성해보세요!</Styled.GuideContainer>
          ) : (
            receiverList.map(receiver => (
              <BigCouponItem.Preview
                key={receiver.id}
                nickname={receiver.nickname}
                message={message}
                hashtag={hashtag}
                couponType={couponType}
              />
            ))
          )}
        </Styled.PreviewContainer>
        <Styled.Inner>
          <CouponCreateForm
            currentReceiverList={receiverList}
            currentType={couponType}
            currentHashtag={hashtag}
            currentColor={color}
            currentMessage={message}
            onSelectReceiver={onSelectReceiver}
            onSelectType={onSelectType}
            onSelectHashtag={onSelectHashtag}
            onSelectColor={onSelectColor}
            onChangeMessage={onChangeMessage}
            onSubmitCreateForm={onSubmitForm}
          />
        </Styled.Inner>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponCreatePage;

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

    height: 170px;

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
