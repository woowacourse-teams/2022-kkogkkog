import { useMemo, useState } from 'react';

import ListFilter from '@/@components/@shared/ListFilter';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { useStatus } from '@/@hooks/@common/useStatus';
import { useChangeKkogKkogStatus } from '@/@hooks/kkogkkog/useChangeKkogKkogStatus';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogModal from '../KkogKkogModal';
import * as Styled from './style';

const filterOption = ['요청', '승인', '대기', '사용'] as const;

const translateKorean = {
  요청: 'REQUESTED',
  승인: 'ACCEPTED',
  대기: 'READY',
  사용: 'FINISHED',
} as const;

type ReceivedKkogKkogFilterOptionType = typeof filterOption[number];

interface ReceivedKkogKkogProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const ReceivedKkogKkog = (props: ReceivedKkogKkogProps) => {
  const { kkogkkogList } = props;

  const { status, changeStatus } = useStatus<ReceivedKkogKkogFilterOptionType>('요청');

  const [clickedCoupon, setClickedCoupon] = useState<KkogKKogResponse | null>(null);

  const changeKkogKkogStatusMutation = useChangeKkogKkogStatus();

  const onClickFilterButton = (status: ReceivedKkogKkogFilterOptionType) => {
    changeStatus(status);
  };

  const onClickCoupon = (kkogkkog: KkogKKogResponse) => {
    setClickedCoupon(kkogkkog);
  };

  const onCloseModal = () => {
    setClickedCoupon(null);
  };

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

  const statusData: Record<
    string,
    {
      description: string;
      modalTitle: string;
      modalButtons?: { text: string; onClick: (args: { id: number; message?: string }) => void }[];
    }
  > = {
    REQUESTED: {
      description: '사용 요청을 한',
      modalTitle: '쿠폰 사용 요청을 취소하시겠어요?',
      modalButtons: [
        {
          text: '요청 취소',
          onClick({ id, message }) {
            changeKkogKkogStatusMutation.mutate({ id, body: { couponEvent: 'CANCEL', message } });
          },
        },
      ],
    },
    READY: {
      description: '사용 하지 않은',
      modalTitle: '쿠폰을 사용하시겠어요?',
      modalButtons: [
        {
          text: '사용 요청',
          onClick({ id, message }) {
            changeKkogKkogStatusMutation.mutate({ id, body: { couponEvent: 'REQUEST', message } });
          },
        },
        {
          text: '사용 완료',
          onClick({ id, message }) {
            console.log('사용 완료!');
            // changeKkogKkogStatusMutation.mutate({ id, body: { couponEvent: 'FINISH', message } });
          },
        },
      ],
    },
    ACCEPTED: {
      description: '사용 승인 받은',
      modalTitle: '쿠폰 사용하셨나요??',
      modalButtons: [
        {
          text: '사용 완료',
          onClick({ id, message }) {
            console.log('사용 완료!');
            // changeKkogKkogStatusMutation.mutate({ id, body: { couponEvent: 'FINISH', message } });
          },
        },
      ],
    },
    FINISHED: {
      description: '사용한',
      modalTitle: '이미 사용한 쿠폰입니다.',
    },
  };

  return (
    <Styled.Root>
      <ListFilter<ReceivedKkogKkogFilterOptionType>
        status={status}
        onClickFilterButton={onClickFilterButton}
        options={filterOption}
      />
      <Styled.ListContainer>
        <div>{statusData[translateKorean[status]].description} 꼭꼭</div>
        <KkogKkogList
          kkogkkogList={parsedKkogKkogList[translateKorean[status]]}
          onClickCoupon={onClickCoupon}
        />
      </Styled.ListContainer>
      {clickedCoupon && (
        <KkogKkogModal
          kkogkkog={clickedCoupon}
          onCloseModal={onCloseModal}
          modalTitle={statusData[clickedCoupon.couponStatus].modalTitle}
          modalButtons={statusData[clickedCoupon.couponStatus].modalButtons}
        />
      )}
    </Styled.Root>
  );
};

export default ReceivedKkogKkog;

ReceivedKkogKkog.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <ListFilter.Skeleton />
      <KkogKkogList.Skeleton />
    </Styled.Root>
  );
};
