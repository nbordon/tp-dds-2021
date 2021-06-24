package imagenormalization;

public enum ImageQuality {

    LOW {
        @Override
        public Double value() {
            return 0.2;
        }
    },
    MEDIUM {
        @Override
        public Double value() {
            return 0.5;
        }
    },
    HIGH {
        @Override
        public Double value() {
            return 0.8;
        }
    },
    ULTRA {
        @Override
        public Double value() {
            return 1.0;
        }
    };

    public abstract Double value();
}