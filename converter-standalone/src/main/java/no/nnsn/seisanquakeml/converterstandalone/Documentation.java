package no.nnsn.seisanquakeml.converterstandalone;

public class Documentation {
    public static void printDocumentation() {
        System.out.println("=== Nor2qml Argument Documentation ===\n");

        System.out.println("General Arguments:");
        System.out.println(String.format("%-15s %-20s %-15s %s", "Key", "Value type", "Default value", "Description"));
        System.out.println(repeat("-", 90));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--convert", "\"q\" or \"s\" (String)", "\"q\"",
                "Specify conversion format. \"q\": s-files to QuakeML, \"s\": QuakeML to s-files."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--input", "Path (String)", "Current path",
                "Input files location. Defaults to current folder."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--output", "Path (String)", "Current path",
                "Output file location. Defaults to current folder. Output filename depends on conversion type."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--version", "\"nordic\", \"nordic2\", \"1.2\", \"2.0\"", "\"1.2\" or \"nordic\"",
                "Specify version of output format (Nordic or QuakeML)."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--eventtype", "String", "\"earthquake\"",
                "Default event type in QuakeML Event element. Non-matching strings omit event type."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--eventcertainty", "\"known\" or \"suspected\"", "\"suspected\"",
                "Default event certainty in QuakeML Event element. Non-matching strings omit certainty."));

        System.out.println("\nQuakeML Specific Arguments:");
        System.out.println(String.format("%-15s %-20s %-15s %s", "Key", "Value type", "Default value", "Description"));
        System.out.println(repeat("-", 90));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--prefix", "\"smi\" or \"quakeml\"", "\"smi\"",
                "Prefix for generation of IDs."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--agency", "String", "\"authorityid\"",
                "Agency for generation of IDs (e.g., \"no.nnsn\")."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--source", "\"file\" or \"ws\"", "\"file\"",
                "Specify if source is a file or a web-service."));
        System.out.println(String.format("%-15s %-20s %-15s %s", "--url", "String (mandatory if --source=ws)", "",
                "URL of web-service source (required if --source=ws)."));

        System.out.println("\n=============================================\n");
    }

    private static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
}
