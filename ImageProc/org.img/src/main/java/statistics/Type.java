package statistics;

/**
 * Created by Lasse on 02/08/2016.
 */
public enum Type {
    GAUSSIAN_FILTER("Gaussian Filter", "1.0", TestName.BLURRING);

    private String name;
    private String version;
    private TestName testName;

    Type(String name, String version, TestName testName){
        this.name = name;
        this.version = version;
        this.testName = testName;
    }

    public String getName(){
        return name;
    }

    public String getVersion() {
        return version;
    }

    public TestName getTestName() {
        return testName;
    }
}
