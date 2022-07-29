import { FunctionComponent } from 'react';

import { KkogKKogResponse } from '@/types/remote/response';

import { BigKkogKkogItemProps } from '../KkogKkogItem/big';
import * as Styled from './vertical.style';

interface VerticalKkogKkogListProps {
  kkogkkogList?: KkogKKogResponse[];
  CouponItem: FunctionComponent<BigKkogKkogItemProps>;
  onClickCouponItem?: (kkogkkog: KkogKKogResponse) => void;
}

const VerticalKkogKkogList = (props: VerticalKkogKkogListProps) => {
  const { kkogkkogList, onClickCouponItem, CouponItem } = props;

  if (kkogkkogList?.length === 0) {
    return (
      <Styled.Root>
        <Styled.TextContainer fontSize='40px'>😱</Styled.TextContainer>
        <Styled.TextContainer>해당 꼭꼭이 존재하지 않아요 ㅠㅠ</Styled.TextContainer>
      </Styled.Root>
    );
  }

  return (
    <Styled.Root>
      {kkogkkogList?.map(kkogkkog => (
        <CouponItem key={kkogkkog.id} onClick={() => onClickCouponItem?.(kkogkkog)} {...kkogkkog} />
      ))}
    </Styled.Root>
  );
};

export default VerticalKkogKkogList;

interface VerticalKkogKkogListSkeletonProps {
  CouponItemSkeleton: FunctionComponent;
}

VerticalKkogKkogList.Skeleton = function Skeleton(props: VerticalKkogKkogListSkeletonProps) {
  const { CouponItemSkeleton } = props;

  return (
    <Styled.Root>
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
    </Styled.Root>
  );
};
