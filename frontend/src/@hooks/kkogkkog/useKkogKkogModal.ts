import { useState } from 'react';

import { useModal } from '@/@hooks/@common/useModal';
import { KkogKKogResponse } from '@/types/remote/response';

const useKkogKkogModal = () => {
  const [clickedKkogKkog, setClickedKkogKkog] = useState<
    (KkogKKogResponse & { thumbnail: string }) | null
  >(null);

  const { isShowModal, closeModal, openModal } = useModal();

  const clickKkogKkog = (kkogkkog: KkogKKogResponse & { thumbnail: string }) => {
    setClickedKkogKkog(kkogkkog);
    openModal();
  };

  return {
    clickedKkogKkog,
    clickKkogKkog,
    isShowModal,
    closeModal,
  };
};

export default useKkogKkogModal;
