import React from 'react';

import { SmallCouponItemProps } from '@/@components/kkogkkog/KkogKkogItem/small';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './horizontal.style';

interface HorizontalKkogKkogListProps {
  kkogkkogList?: KkogKKogResponse[];
  onClickCouponItem?: (kkogkkog: KkogKKogResponse) => void;
  CouponItem: React.FunctionComponent<SmallCouponItemProps>;
}

const HorizontalCouponList = (props: HorizontalKkogKkogListProps) => {
  const { kkogkkogList, onClickCouponItem, CouponItem } = props;

  if (kkogkkogList?.length === 0) {
    return (
      <Styled.SlideRoot>
        <Styled.TextContainer fontSize='40px'>😱</Styled.TextContainer>
        <Styled.TextContainer>해당 꼭꼭이 존재하지 않아요 ㅠㅠ</Styled.TextContainer>
      </Styled.SlideRoot>
    );
  }

  return (
    <Styled.SlideRoot>
      {kkogkkogList?.map(kkogkkog => (
        <CouponItem key={kkogkkog.id} onClick={() => onClickCouponItem?.(kkogkkog)} {...kkogkkog} />
      ))}
    </Styled.SlideRoot>
  );
};

export default HorizontalCouponList;

interface SkeletonHorizontalKkogKkogListProps {
  CouponItemSkeleton: React.FunctionComponent;
}

HorizontalCouponList.Skeleton = function Skeleton(props: SkeletonHorizontalKkogKkogListProps) {
  const { CouponItemSkeleton } = props;

  return (
    <Styled.SlideRoot>
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
    </Styled.SlideRoot>
  );
};
