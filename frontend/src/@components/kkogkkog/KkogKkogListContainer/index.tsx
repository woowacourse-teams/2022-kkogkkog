import { useMemo } from 'react';
import { useLocation } from 'react-router-dom';

import ListFilter from '@/@components/@shared/ListFilter';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import KkogKkogModal from '@/@components/kkogkkog/KkogKkogModal';
import { useStatus } from '@/@hooks/@common/useStatus';
import useKkogKkogModal from '@/@hooks/kkogkkog/useKkogKkogModal';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

interface KkogKkogListContainerProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const filterOption = ['요청', '승인', '대기', '사용'] as const;

export type KkogKkogListContainerFilterOptionType = typeof filterOption[number];

const KkogKkogListContainer = (props: KkogKkogListContainerProps) => {
  const { kkogkkogList } = props;

  const { state } = useLocation() as { state: { action: string } };

  const { status, changeStatus } = useStatus<KkogKkogListContainerFilterOptionType>(
    state?.action === 'create' ? '대기' : '요청'
  );

  const { currentKkogKkog, isShowKkogKkogModal, openKkogKkogModal, closeKkogKkogModal } =
    useKkogKkogModal();

  const parsedKkogKkogList = useMemo(
    () =>
      kkogkkogList?.reduce(
        (prev, kkogkkog) => {
          const key = kkogkkog.couponStatus;

          return { ...prev, [key]: [...prev[key], kkogkkog] };
        },
        {
          REQUESTED: [],
          READY: [],
          ACCEPTED: [],
          FINISHED: [],
        } as any
      ),
    [kkogkkogList]
  );

  const onClickFilterButton = (status: KkogKkogListContainerFilterOptionType) => {
    changeStatus(status);
  };

  const onClickCouponItem = (kkogkkog: KkogKKogResponse & { thumbnail: string }) => {
    openKkogKkogModal(kkogkkog);
  };

  return (
    <Styled.Root>
      <ListFilter<KkogKkogListContainerFilterOptionType>
        status={status}
        options={filterOption}
        onClickFilterButton={onClickFilterButton}
      />
      {status === '요청' && (
        <Styled.Container>
          <div>
            사용 <span>요청이 온</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['REQUESTED']}
            onClickCouponItem={onClickCouponItem}
          />
        </Styled.Container>
      )}

      {status === '승인' && (
        <Styled.Container>
          <div>
            사용 <span>승인한</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['ACCEPTED']}
            onClickCouponItem={onClickCouponItem}
          />
        </Styled.Container>
      )}

      {status === '대기' && (
        <Styled.Container>
          <div>
            사용을 <span>기다리는</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['READY']}
            onClickCouponItem={onClickCouponItem}
          />
        </Styled.Container>
      )}

      {status === '사용' && (
        <Styled.Container>
          <div>
            <span>사용된</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['FINISHED']}
            onClickCouponItem={onClickCouponItem}
          />
        </Styled.Container>
      )}

      {isShowKkogKkogModal && currentKkogKkog && (
        <KkogKkogModal kkogkkog={currentKkogKkog} closeModal={closeKkogKkogModal} />
      )}
    </Styled.Root>
  );
};

export default KkogKkogListContainer;

KkogKkogListContainer.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <ListFilter.Skeleton />
      <KkogKkogList.Skeleton />
    </Styled.Root>
  );
};
