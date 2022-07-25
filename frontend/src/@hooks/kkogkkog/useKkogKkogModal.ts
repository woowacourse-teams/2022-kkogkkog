import { useState } from 'react';

import { KkogKKogResponse } from '@/types/remote/response';

const useKkogKkogModal = () => {
  const [currentKkogKkog, setCurrentKkogKkog] = useState<KkogKKogResponse | null>(null);

  const openKkogKkogModal = (kkogkkog: KkogKKogResponse) => {
    setCurrentKkogKkog(kkogkkog);
  };

  const closeKkogKkogModal = () => {
    setCurrentKkogKkog(null);
  };

  return {
    currentKkogKkog,
    openKkogKkogModal,
    closeKkogKkogModal,
  };
};

export default useKkogKkogModal;
