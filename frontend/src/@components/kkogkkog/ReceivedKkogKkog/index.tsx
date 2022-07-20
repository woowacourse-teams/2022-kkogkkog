import { useMemo } from 'react';
import { QueryClient, useMutation } from 'react-query';

import ListFilter from '@/@components/@shared/ListFilter';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { useStatus } from '@/@hooks/@common/useStatus';
import { useKkogKkogList } from '@/@hooks/kkogkkog/useKkogKkogList';
import { changeKkogkkogStatus } from '@/apis/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

const filterOption = ['요청', '대기'] as const;

type ReceivedKkogKkogFilterOptionType = typeof filterOption[number];

interface ReceivedKkogKkogProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const ReceivedKkogKkog = (props: ReceivedKkogKkogProps) => {
  const { kkogkkogList } = props;
  const { refetch } = useKkogKkogList();

  const { status, changeStatus } = useStatus<ReceivedKkogKkogFilterOptionType>('요청');

  const onClickFilterButton = (status: ReceivedKkogKkogFilterOptionType) => {
    changeStatus(status);
  };

  const queryClient = new QueryClient();

  const changeStatusMutate = useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries('kkogkkogList');
      refetch();
    },
  });

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
        } as any
      ),
    [kkogkkogList]
  );

  const modalType: Record<
    'REQUESTED' | 'READY',
    {
      modalTitle: string;
      modalButtons: { text: string; onClick: (args: { id: number; message?: string }) => void }[];
    }
  > = {
    REQUESTED: {
      modalTitle: '쿠폰 사용 요청을 취소하시겠어요?',
      modalButtons: [
        {
          text: '요청 취소',
          onClick({ id, message }) {
            changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL', message } });
          },
        },
      ],
    },
    READY: {
      modalTitle: '쿠폰을 사용하시겠어요?',
      modalButtons: [
        {
          text: '사용 요청',
          onClick({ id, message }) {
            changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', message } });
          },
        },
        {
          text: '사용 완료',
          onClick() {
            console.log('사용 완료합니다.');
          },
        },
      ],
    },
  };

  return (
    <Styled.Root>
      <ListFilter<ReceivedKkogKkogFilterOptionType>
        status={status}
        onClickFilterButton={onClickFilterButton}
        options={filterOption}
      />
      {status === '요청' && (
        <Styled.Container>
          <div>
            사용 <span>요청을 한</span> 꼭꼭
          </div>
          <KkogKkogList kkogkkogList={parsedKkogKkogList['REQUESTED']} {...modalType.REQUESTED} />
        </Styled.Container>
      )}

      {status === '대기' && (
        <Styled.Container>
          <div>
            사용 <span>하지 않은</span> 꼭꼭
          </div>
          <KkogKkogList kkogkkogList={parsedKkogKkogList['READY']} {...modalType.READY} />
        </Styled.Container>
      )}
    </Styled.Root>
  );
};

export default ReceivedKkogKkog;
