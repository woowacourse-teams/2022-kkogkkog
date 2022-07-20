import { css } from '@emotion/react';
import { useState } from 'react';

import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import { THUMBNAIL } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogModal from '../KkogKkogModal';
import * as Styled from './style';

interface KkogKkogListProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const KkogKkogList = (props: KkogKkogListProps) => {
  const { kkogkkogList } = props;
  const [clickedKkogKkog, setClickedKkogKkog] = useState<KkogKKogResponse | null>(null);

  const onClickKkogKkog = (kkogkkog: KkogKKogResponse & { thumbnail: string }) => {
    setClickedKkogKkog(kkogkkog);
  };

  const onCloseModal = () => {
    setClickedKkogKkog(null);
  };

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
          thumbnail={THUMBNAIL[kkogkkog.couponType]}
          onClick={() =>
            onClickKkogKkog({ ...kkogkkog, thumbnail: THUMBNAIL[kkogkkog.couponType] })
          }
          {...kkogkkog}
        />
      ))}
      {clickedKkogKkog && (
        <KkogKkogModal clickedKkogKkog={clickedKkogKkog} onCloseModal={onCloseModal} />
      )}
    </Styled.Root>
  );
};

export default KkogKkogList;
