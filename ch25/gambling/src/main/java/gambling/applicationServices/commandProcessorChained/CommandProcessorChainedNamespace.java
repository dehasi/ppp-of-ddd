package gambling.applicationServices.commandProcessorChained;

class CommandProcessorChainedNamespace {

    public interface ICommandProcessor<T> {
        void process(T command);
    }

    public record RecommendAFriend(int referrerId) {}

    public static class RecommendAFriendProcessor implements ICommandProcessor<RecommendAFriend> {
        @Override public void process(RecommendAFriend command) {
            System.out.println("Processing ReferAFriend command");
        }
    }

    public static class LoggingProcessor<T> implements ICommandProcessor<T> {

        private final ICommandProcessor<T> nextLinkInChain;

        public LoggingProcessor(ICommandProcessor<T> processor) {
            this.nextLinkInChain = processor;
        }

        @Override public void process(T command) {
            // log something before
            nextLinkInChain.process(command);
            // log something after
        }
    }

    public static class TransactionProcessor<T> implements ICommandProcessor<T> {
        private final ICommandProcessor<T> nextLinkInChain;

        public TransactionProcessor(ICommandProcessor<T> processor) {
            this.nextLinkInChain = processor;
        }

        @Override public void process(T command) {
            // start transaction
            try {
                nextLinkInChain.process(command);
                // commit transaction
            } catch (Exception e) {
                // rollback transaction
            }
        }
    }

    public static class Bootstrap {
        public static ICommandProcessor<RecommendAFriend> referAFriendProcessor;

        public static void configureApplication() {
            // create inner processor
            var referAFriendProcessor = new RecommendAFriendProcessor();
            // wrap inner processor with logging
            var loggingProcessor = new LoggingProcessor<>(referAFriendProcessor);
            // wrap logging processor (that wraps inner) with a transaction
            var transactionProcessor = new TransactionProcessor<>(loggingProcessor);

            Bootstrap.referAFriendProcessor = transactionProcessor;

            // alternatively, you can use dependency injection
        }
    }
}
