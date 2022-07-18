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

  const handleClickKkogKkog = (kkogkkog: KkogKKogResponse & { thumbnail: string }) => {
    setClickedKkogKkog(kkogkkog);
  };

  const handleCloseModal = () => {
    setClickedKkogKkog(null);
  };

  return (
    <Styled.Root>
      {kkogkkogList?.map(kkogkkog => (
        <KkogKkogItem
          key={kkogkkog.id}
          thumbnail={THUMBNAIL[kkogkkog.couponType]}
          onClick={() =>
            handleClickKkogKkog({ ...kkogkkog, thumbnail: THUMBNAIL[kkogkkog.couponType] })
          }
          {...kkogkkog}
        />
      ))}
      {clickedKkogKkog && (
        <KkogKkogModal clickedKkogKkog={clickedKkogKkog} handleCloseModal={handleCloseModal} />
      )}
    </Styled.Root>
  );
};

export default KkogKkogList;
