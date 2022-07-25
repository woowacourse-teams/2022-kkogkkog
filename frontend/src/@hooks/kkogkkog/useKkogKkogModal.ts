import { useState } from 'react';

import { useModal } from '@/@hooks/@common/useModal';
import { KkogKKogResponse } from '@/types/remote/response';

const useKkogKkogModal = () => {
  const [currentKkogKkog, setCurrentKkogKkog] = useState<KkogKKogResponse | null>(null);

  const { isShowModal, closeModal, openModal } = useModal();

  const openKkogKkogModal = (kkogkkog: KkogKKogResponse) => {
    setCurrentKkogKkog(kkogkkog);
    openModal();
  };

  return {
    currentKkogKkog,
    isShowKkogKkogModal: isShowModal,
    openKkogKkogModal,
    closeKkogKkogModal: closeModal,
  };
};

export default useKkogKkogModal;
