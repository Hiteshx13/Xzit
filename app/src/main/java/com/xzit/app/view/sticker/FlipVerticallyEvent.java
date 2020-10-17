package com.xzit.app.view.sticker;

/**
 * @author wupanjie
 */

public class FlipVerticallyEvent extends AbstractFlipEvent {

  @Override @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_VERTICALLY;
  }
}
