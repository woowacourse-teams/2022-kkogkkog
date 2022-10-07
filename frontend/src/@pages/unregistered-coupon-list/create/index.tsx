import { useEffect, useRef } from 'react';

import PageTemplate from '@/@components/@shared/PageTemplate';
import UnregisteredCouponCreateForm from '@/@components/unregistered-coupon/UnregisteredCouponCreateForm';
import { useUnregisteredForm } from '@/@hooks/ui/unregistered-coupon/useUnregisteredForm';

import * as Styled from './style';

const UnregisteredCouponCreatePage = () => {
  const elementRef = useRef<HTMLDivElement>(null);

  const {
    state: { couponCount, couponMessage, couponTag, couponType },
    changeHandler: {
      onChangeCouponCount,
      onChangeCouponMessage,
      onSelectCouponTag,
      onSelectCouponType,
    },
    submitHandler: { create: onSubmitUnregisteredCouponCreateForm },
  } = useUnregisteredForm();

  useEffect(() => {
    elementRef.current?.scrollTo({ left: elementRef.current?.scrollWidth, behavior: 'smooth' });
  }, [couponCount]);

  return (
    <PageTemplate title='쿠폰 보내기'>
      <Styled.Root>
        <Styled.PreviewContainer ref={elementRef}>
          {/* {receiverList.length === 0 ? (
            <Styled.GuideContainer>쿠폰을 완성해보세요!</Styled.GuideContainer>
          ) : (
            receiverList.map(receiver => (
              <BigCouponItem.Preview
                key={receiver.id}
                receiver={receiver}
                couponMessage={couponMessage}
                couponTag={couponTag}
                couponType={couponType}
              />
            ))
          )} */}
        </Styled.PreviewContainer>
        <Styled.Inner>
          <UnregisteredCouponCreateForm
            currentCouponCount={couponCount}
            currentCouponType={couponType}
            currentCouponTag={couponTag}
            currentCouponMessage={couponMessage}
            onChangeCouponCount={onChangeCouponCount}
            onSelectCouponType={onSelectCouponType}
            onSelectCouponTag={onSelectCouponTag}
            onChangeCouponMessage={onChangeCouponMessage}
            onSubmitCreateForm={onSubmitUnregisteredCouponCreateForm}
          />
        </Styled.Inner>
      </Styled.Root>
    </PageTemplate>
  );
};

export default UnregisteredCouponCreatePage;
