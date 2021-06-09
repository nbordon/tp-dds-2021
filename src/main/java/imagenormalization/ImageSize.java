package imagenormalization;

public enum ImageSize {

    SMALL {
        @Override
        public int getWidth() {
            return 100;
        }
    },
    MEDIUM {
        @Override
        public int getWidth() {
            return 200;
        }
    },
    BIG {
        @Override
        public int getWidth() {
            return 300;
        }
    },
    HUGE {
        @Override
        public int getWidth() {
            return 400;
        }
    };

    public abstract int getWidth();

    public int getHeight() {
        return getWidth();
    }
}
