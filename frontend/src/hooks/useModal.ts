import { useCallback, useState } from 'react';

export const useModal = () => {
  const [isShowModal, setIsShowModal] = useState(false);

  const openModal = useCallback(() => {
    setIsShowModal(true);
  }, [setIsShowModal]);

  const closeModal = useCallback(() => {
    setIsShowModal(false);
  }, [setIsShowModal]);

  const toggleModal = useCallback(() => {
    setIsShowModal(prev => !prev);
  }, [setIsShowModal]);

  return { isShowModal, openModal, closeModal, toggleModal };
};
