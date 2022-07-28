import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

interface KkogKkogListProps {
  kkogkkogList?: KkogKKogResponse[];
  onClickCouponItem?: (kkogkkog: KkogKKogResponse) => void;
}

const KkogKkogList = (props: KkogKkogListProps) => {
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
        <KkogKkogItem
          key={kkogkkog.id}
          onClick={() => onClickCouponItem?.(kkogkkog)}
          {...kkogkkog}
        />
      ))}
    </Styled.Root>
  );
};

export default KkogKkogList;

KkogKkogList.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <KkogKkogItem.Skeleton />
      <KkogKkogItem.Skeleton />
      <KkogKkogItem.Skeleton />
      <KkogKkogItem.Skeleton />
    </Styled.Root>
  );
};
