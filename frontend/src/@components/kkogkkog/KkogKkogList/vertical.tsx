import BigKkogKkogItem from '@/@components/kkogkkog/KkogKkogItem/big';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

interface VerticalKkogKkogListProps {
  kkogkkogList?: KkogKKogResponse[];
  onClickCouponItem?: (kkogkkog: KkogKKogResponse) => void;
}

const VerticalKkogKkogList = (props: VerticalKkogKkogListProps) => {
  const { kkogkkogList, onClickCouponItem } = props;

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
        <BigKkogKkogItem
          key={kkogkkog.id}
          onClick={() => onClickCouponItem?.(kkogkkog)}
          {...kkogkkog}
        />
      ))}
    </Styled.Root>
  );
};

export default VerticalKkogKkogList;

VerticalKkogKkogList.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <BigKkogKkogItem.Skeleton />
      <BigKkogKkogItem.Skeleton />
      <BigKkogKkogItem.Skeleton />
      <BigKkogKkogItem.Skeleton />
    </Styled.Root>
  );
};
