import { ChangeEventHandler, FormEventHandler, KeyboardEvent, MouseEventHandler } from 'react';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import SelectInput from '@/@components/@shared/SelectInput';
import UserSearchModal from '@/@components/user/UserSearchModal';
import { useModal } from '@/@hooks/@common/useModal';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { PATH } from '@/Router';
import {
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponHashtags,
  couponTypeCollection,
  THUMBNAIL,
} from '@/types/coupon/client';
import { UserResponse } from '@/types/user/remote';

import * as Styled from './style';

interface CouponCreateFormProps {
  currentReceiverList: UserResponse[];
  currentCouponType: COUPON_ENG_TYPE;
  currentCouponTag: COUPON_HASHTAGS;
  currentCouponMessage: string;
  onSelectReceiver: (user: UserResponse) => MouseEventHandler<HTMLButtonElement>;
  onSelectCouponType: (type: COUPON_ENG_TYPE) => MouseEventHandler<HTMLButtonElement>;
  onSelectCouponTag: (hashtag: COUPON_HASHTAGS) => MouseEventHandler<HTMLButtonElement>;
  onChangeCouponMessage: ChangeEventHandler<HTMLTextAreaElement>;
  onSubmitCouponCreateForm: FormEventHandler<HTMLFormElement>;
}

const CouponCreateForm = (props: CouponCreateFormProps) => {
  const {
    currentReceiverList,
    currentCouponType,
    currentCouponTag,
    currentCouponMessage,
    onSelectReceiver,
    onSelectCouponType,
    onSelectCouponTag,
    onChangeCouponMessage,
    onSubmitCouponCreateForm,
  } = props;

  usePreventReload();

  const { isShowModal, openModal, closeModal } = useModal();

  return (
    <Styled.FormRoot onSubmit={onSubmitCouponCreateForm}>
      <Styled.FindUserContainer
        aria-label='유저 검색 섹션. 활성화하려면 엔터를 눌러주세요'
        tabIndex={0}
      >
        <label htmlFor='find-user-input'>누구에게 보내시나요 ?</label>
        <Styled.FindUserInput id='find-user-input' type='button' onClick={openModal}>
          {currentReceiverList.length === 0 && <span>유저를 찾아보세요</span>}

          {currentReceiverList.length !== 0 && (
            <Styled.SelectedUserListContainer>
              {currentReceiverList.map(receiver => (
                <Styled.SelectedUserContainer key={receiver.id}>
                  <span>{receiver.nickname}</span>
                </Styled.SelectedUserContainer>
              ))}
            </Styled.SelectedUserListContainer>
          )}

          <span>🔍</span>
        </Styled.FindUserInput>
        <Link to={PATH.UNREGISTERED_COUPON_CREATE} css={Styled.AnotherCouponCreatePageLink} replace>
          미등록 쿠폰 생성하기
        </Link>
      </Styled.FindUserContainer>

      {isShowModal && (
        <UserSearchModal
          currentReceiverList={currentReceiverList}
          onSelectReceiver={onSelectReceiver}
          closeModal={closeModal}
        />
      )}

      <SelectInput label='어떤 쿠폰인가요 ?'>
        {couponTypeCollection.map(({ engType, koreanType }) => (
          <Styled.TypeOption key={engType} isSelected={engType === currentCouponType}>
            <button type='button' onClick={onSelectCouponType(engType)}>
              <img src={THUMBNAIL[engType]} alt={koreanType} width={50} height={50} />
            </button>
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput.VerticalView label='당신의 마음을 선택해주세요 !'>
        {couponHashtags.map(hashtag => (
          <Styled.FeelOption key={hashtag} isSelected={hashtag === currentCouponTag}>
            <button type='button' onClick={onSelectCouponTag(hashtag)}>
              #{hashtag}
            </button>
          </Styled.FeelOption>
        ))}
      </SelectInput.VerticalView>

      <Styled.TextareaContainer>
        <label htmlFor='message-textarea'>메세지를 작성해보세요 (선택)</label>
        <Styled.MessageTextareaContainer>
          <Styled.MessageTextarea
            id='message-textarea'
            placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
            value={currentCouponMessage}
            onChange={onChangeCouponMessage}
          />
          <Styled.MessageLength>{currentCouponMessage.length} / 50</Styled.MessageLength>
        </Styled.MessageTextareaContainer>
      </Styled.TextareaContainer>

      <Styled.ButtonContainer>
        <Styled.ButtonInner>
          <Button>쿠폰 보내기</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Styled.FormRoot>
  );
};

export default CouponCreateForm;
