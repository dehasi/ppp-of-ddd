package ch.model;

import ch.model.baskets.BasketItem;
import me.dehasi.replacements.exception.NotImplementedException;

import java.util.List;
import java.util.UUID;

public class PromotionsNamespace {

    public record Coupon(String code) {}

    public enum CouponIssues {
        NotApplied(0),
        NoIssue(1);

        private final int val;

        CouponIssues(int i) {
            this.val = i;
        }
    }

    public interface ICouponPolicy {}

    public interface IPromotionsRepository  /*IRepository<Promotion, UUID>*/ {
        void add(Promotion promotion);
        Promotion find_by(String voucher_code);
    }

    public record Promotion(String voucher_code, Money discount, Money threshold) {
        public boolean is_still_active() {
            return true;
        }

        public CouponIssues reason_why_cannot_be_applied_to(List<BasketItem> items) {
            throw new NotImplementedException();
        }

        public Money calculate_discount_for(List<BasketItem> items) {
            throw new NotImplementedException();
        }

        public boolean is_applicable_for(List<BasketItem> items) {
            return true;
        }

        public Coupon create_coupon_for(UUID basket_id) {
            return new Coupon(UUID.randomUUID().toString());
        }
    }

    public static class PromotionNotApplicableException extends DomainException {
        public PromotionNotApplicableException(String messageForCustomer) {
            super(messageForCustomer);
        }
    }
}
